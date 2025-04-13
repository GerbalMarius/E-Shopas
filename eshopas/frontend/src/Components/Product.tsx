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
    const [quantity, setQuantity] = useState<number>(1);

    const putProductInCart = async (productId: number) => {
        try {
            const response = await fetch(`${BACKEND_PREFIX}/api/product/add_to_cart`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ productId, quantity }),
            });

            if (response.ok) {
                const units = await response.json();
                if (quantity != 0 && units == 0){
                    alert('Product is out of stock!');
                }else if (units == 1){
                    alert('Added '+ units + ' product to cart!');
                }else{
                    alert('Added '+ units + ' products to cart!');
                }
                window.location.reload();
            } else {
                alert('Failed to add to cart.');
            }
        } catch (err) {
            console.error(err);
            alert('Error adding to cart');
        }
    };

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
                <div className="action-btn-container" style={{ display: 'flex', alignItems: 'center',justifyContent: 'center', gap: '10px' }}>
                    <button
                        className="action-btn"
                        style={{
                            fontSize: '14px',
                            padding: '6px 12px',
                            width: 'auto', // prevent full width
                            whiteSpace: 'nowrap',
                        }}
                        onClick={() => putProductInCart(product.id)}
                    >
                        Add to Cart
                    </button>
                    <input
                        type="number"
                        min={1}
                        value={quantity}
                        onChange={(e) => setQuantity(parseInt(e.target.value))}
                        style={{
                            width: '60px',
                            padding: '4px 8px',
                            fontSize: '14px',
                            borderRadius: '4px',
                            border: '1px solid #ccc',
                        }}
                    />
                </div>
                <div className="action-btn-container">
                    <button className="action-btn">Reviews</button>
                </div>
                <div className="action-btn-container">
                    <button className="action-btn">Leave a review</button>
                </div>
            </div>
        </div>
    );
};

export default Product;
