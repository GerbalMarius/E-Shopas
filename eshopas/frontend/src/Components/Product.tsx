import { useParams } from "react-router-dom";
import React from "react";

const Product = () => {
    const { id } = useParams();

    return (
        <div style={{ padding: "40px" }}>
            <h1>Product Details</h1>
            <p>This is detailed information for product ID: {id}</p>
            {/* You can fetch product data using `id` here */}
        </div>
    );
};

export default Product;