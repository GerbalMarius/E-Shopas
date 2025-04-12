import { useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { BACKEND_PREFIX } from "../App";

interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    units: number;
    pictureBase64?: string;
    categoryId: number;
    categoryName: string; // Pridėtas categoryName
    manufacturerId: number;
    manufacturerName: string; // Pridėtas manufacturerName
    discount: number;
}

const Product = () => {
    const { id } = useParams(); // Get the product ID from the URL
    const [product, setProduct] = useState<Product | null>(null);

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await axios.get<Product>(`${BACKEND_PREFIX}/api/product/${id}`);
                setProduct(response.data);
            } catch (err) {
                console.error("Error fetching product:", err);
            }
        };

        if (id) {
            fetchProduct();
        }
    }, [id]);

    if (!product) {
        return <p>Loading product details...</p>;
    }

    return (
        <div className="page-container">
            <div className="page-header">
                <h1>{product.name}</h1>
                <p>{product.description}</p>
            </div>

            <div className="product-details">
                <div className="product-card-wrapper">
                    {product.pictureBase64 && (
                        <img
                            src={`data:image/jpeg;base64,${product.pictureBase64}`}
                            alt={product.name}
                            className="product-image"
                        />
                    )}
                    <h2 className="product-price-tag">{product.price.toFixed(2)}€</h2>
                    <p><strong>Discount:</strong> {product.discount}%</p>
                    <p><strong>Units Available:</strong> {product.units}</p>
                    <div>
                        <h3>Category: {product.categoryName}</h3>
                        <h3>Manufacturer: {product.manufacturerName}</h3>
                    </div>
                </div>
                <div className="action-btn-container">
                    <button className="action-btn">Add to Cart</button>
                </div>
            </div>
        </div>
    );
};

export default Product;
