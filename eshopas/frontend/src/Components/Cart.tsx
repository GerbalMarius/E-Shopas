import {Link} from "react-router-dom";
import React from "react";

const Cart = () => {
    const cartItems = [
        { id: 1, name: "Product A", quantity: 2, price: 19.99 },
        { id: 2, name: "Product B", quantity: 1, price: 9.99 },
    ];

    const total = cartItems.reduce((sum, item) => sum + item.quantity * item.price, 0);

    return (
        <div className="cart-container">
            <h1 className="cart-title">This is your current cart</h1>

            <table className="cart-table">
                <thead>
                <tr>
                    <th>Item</th>
                    <th>Qty</th>
                    <th>Unit Price</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                {cartItems.map((item) => (
                    <tr key={item.id}>
                        <td>{item.name}</td>
                        <td>{item.quantity}</td>
                        <td>{item.price.toFixed(2)}€</td>
                        <td>{(item.quantity * item.price).toFixed(2)}€</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <div className="cart-footer">
                <p><strong>Total:</strong> {total.toFixed(2)}€</p>
                <Link to="/order">Submit order</Link>
            </div>
        </div>
    );
};

export default Cart;