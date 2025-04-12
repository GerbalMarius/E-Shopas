import React, { useEffect, useState } from "react";
import {
    Elements,
    useStripe,
    useElements,
    CardElement,
} from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import axios from "axios";
import { BACKEND_PREFIX } from "../App";

interface CartItem {
    name: string;
    price: number;
    quantity: number;
}

const stripePromise = loadStripe('pk_test_51RCxrYP1iMohmZdgBqsvXzyv6vehxRTEhm1uRmodHcWaDAwDJ2yf5pcqPVb3ORrMA5kijg9LFk3xdfjmqFL5SjBC00gNHWLaRF');

interface CheckoutFormProps {
    cartItems: CartItem[];
}

const CheckoutForm: React.FC<CheckoutFormProps> = ({ cartItems }) => {
    const stripe = useStripe();
    const elements = useElements();

    const [validationErrors, setValidationErrors] = useState<Record<string, string[]> | null>(null);
    const [clientSecret, setClientSecret] = useState<string | null>(null);
    const [processing, setProcessing] = useState(false);
    const [success, setSuccess] = useState<string | null>(null);
    const [error, setError] = useState<string>("");

    // Customer fields
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [address, setAddress] = useState("");
    const [telephoneNumber, setTelephoneNumber] = useState("");
    const [email, setEmail] = useState("");

    useEffect(() => {
        axios
            .post(`${BACKEND_PREFIX}/api/order/payment-intent`, cartItems, { withCredentials: true })
            .then((res) => setClientSecret(res.data.clientSecret))
            .catch(() => setError("Failed to load payment intent"));
    }, [cartItems]);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!stripe || !elements || !clientSecret) return;

        setProcessing(true);
        setValidationErrors(null);
        setError("");
        setSuccess(null);

        const cardElement = elements.getElement(CardElement);
        if (!cardElement) {
            setError("Card element not found.");
            setProcessing(false);
            return;
        }

        const result = await stripe.confirmCardPayment(clientSecret, {
            payment_method: {
                card: cardElement,
                billing_details: {
                    name: `${firstName} ${lastName}`,
                    email,
                    phone: telephoneNumber,
                    address: {
                        line1: address,
                    },
                },
            },
        });

        if (result.error) {
            setError(result.error.message ?? "Payment failed.");
        } else if (result.paymentIntent?.status === "succeeded") {
            const paymentData = {
                name: `${firstName} ${lastName}`,
                email,
                phone: telephoneNumber,
                address: address,
                paymentIntentId: result.paymentIntent.id
            };

            try {
                await axios.post(`${BACKEND_PREFIX}/api/order/submit-payment`, paymentData, {
                    withCredentials: true,
                });
                setSuccess("✅ Payment successful!");
            } catch (err: any) {
                if (err.response?.status === 400 && err.response.data) {
                    setValidationErrors(err.response.data);
                } else {
                    setError("Something went wrong while submitting payment details.");
                }
            }
        }

        setProcessing(false);
    };

    return (
        <form onSubmit={handleSubmit} className="checkout-form-wrapper m-auto">
            <h3>Complete Your Order</h3>

            <input
                type="text"
                placeholder="First Name"
                className="form-control my-2"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
            />
            <input
                type="text"
                placeholder="Last Name"
                className="form-control my-2"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
            />
            <input
                type="text"
                placeholder="Address"
                className="form-control my-2"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
            />
            <input
                type="tel"
                placeholder="Phone Number"
                className="form-control my-2"
                value={telephoneNumber}
                onChange={(e) => setTelephoneNumber(e.target.value)}
            />
            <input
                type="email"
                placeholder="Email"
                className="form-control my-2"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />

            <CardElement
                options={{
                    style: {
                        base: {
                            fontSize: "16px",
                            color: "#32325d",
                            "::placeholder": { color: "#a0a0a0" },
                        },
                        invalid: { color: "#fa755a" },
                    },
                }}
            />

            {error && <div className="alert alert-danger mt-3">{error}</div>}
            {success && <div className="alert alert-success mt-3">{success}</div>}
            {validationErrors && (
                <div className="alert alert-danger mt-3">
                    <ul className="mb-0">
                        {Object.entries(validationErrors).map(([field, messages]) =>
                            messages.map((msg, index) => (
                                <li key={`${field}-${index}`}>{msg}</li>
                            ))
                        )}
                    </ul>
                </div>
            )}

            <button
                className="btn btn-purple-gradient"
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

    const getTotal = () =>
        cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2);

    return (
        <div className="page-container p-4">
            <h1 style={{ textAlign: "center", marginTop: "20px" }}>Order Checkout</h1>
            <div className="d-flex flex-wrap justify-content-center mt-4 gap-4">
                <div className="cart-summary card p-3" style={{ minWidth: "300px", maxWidth: "400px" }}>
                    <h4>🛒 Your Cart</h4>
                    <ul className="list-group list-group-flush">
                        {cartItems.map((item, index) => (
                            <li key={index} className="list-group-item d-flex justify-content-between">
                                <span>{item.name} × {item.quantity}</span>
                                <span>€{(item.price * item.quantity).toFixed(2)}</span>
                            </li>
                        ))}
                        <li className="list-group-item d-flex justify-content-between fw-bold">
                            <span>Total</span>
                            <span>€{getTotal()}</span>
                        </li>
                    </ul>
                </div>

                <div style={{ minWidth: "320px", maxWidth: "500px", flexGrow: 1 }}>
                    <Elements stripe={stripePromise}>
                        <CheckoutForm cartItems={cartItems} />
                    </Elements>
                </div>
            </div>
        </div>
    );
};

export default Order;
