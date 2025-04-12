import React, { useEffect, useState } from "react";
import {
    Elements,
    useStripe,
    useElements,
    CardElement,
} from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import axios from "axios";

interface CartItem {
    name: string;
    price: number;
    quantity: number;
}
const stripePromise =
    loadStripe('pk_test_51RCxrYP1iMohmZdgBqsvXzyv6vehxRTEhm1uRmodHcWaDAwDJ2yf5pcqPVb3ORrMA5kijg9LFk3xdfjmqFL5SjBC00gNHWLaRF');


interface CheckoutFormProps {
    cartItems: CartItem[];
}

const CheckoutForm: React.FC<CheckoutFormProps> = ({ cartItems }) => {
    const stripe = useStripe();
    const elements = useElements();

    const [clientSecret, setClientSecret] = useState<string | null>(null);
    const [processing, setProcessing] = useState(false);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        axios
            .post("http://localhost:8080/api/order/payment-intent", {
                totalPrice: cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0),
            }) // Spring Boot backend URL
            .then((res) => setClientSecret(res.data.clientSecret))
            .catch(() => setError("Failed to load payment intent"));
    }, [cartItems]);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!stripe || !elements || !clientSecret) return;

        setProcessing(true);

        const cardElement = elements.getElement(CardElement);
        if (!cardElement) {
            setError("Card element not found.");
            setProcessing(false);
            return;
        }

        const result = await stripe.confirmCardPayment(clientSecret, {
            payment_method: {
                card: cardElement,
            },
        });

        if (result.error) {
            setError(result.error.message ?? "Payment failed.");
            setProcessing(false);
        } else if (result.paymentIntent?.status === "succeeded") {
            alert("✅ Payment successful!");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="modern-form" style={{ maxWidth: 500 }}>
            <h3 style={{ textAlign: "center" }}>Complete Your Order</h3>
            <CardElement
                options={{
                    style: {
                        base: {
                            fontSize: "16px",
                            color: "#32325d",
                            "::placeholder": {
                                color: "#a0a0a0",
                            },
                        },
                        invalid: {
                            color: "#fa755a",
                        },
                    },
                }}
            />
            {error && <div className="alert alert-danger mt-3">{error}</div>}
            <button
                className="btn btn-purple-gradient mt-4"
                disabled={!stripe || !elements || processing}
                type="submit"
            >
                {processing ? "Processing..." : "Pay Now"}
            </button>
        </form>
    );
};

const Order: React.FC = () => {
    const cartItems: CartItem[] = [
        { name: "Product A", price: 19.99, quantity: 2 },
        { name: "Product B", price: 9.99, quantity: 1 },
    ];

    return (
        <div className="page-container">
            <h1 style={{ textAlign: "center", marginTop: "40px" }}>Order Checkout</h1>
            <Elements stripe={stripePromise}>
                <CheckoutForm cartItems={cartItems} />
            </Elements>
        </div>
    );
};

export default Order;