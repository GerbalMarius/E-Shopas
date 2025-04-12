import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { BACKEND_PREFIX } from "../App";

const AddProduct = () => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState(0);
    const [units, setUnits] = useState(0);
    const [categoryId, setCategoryId] = useState(1);
    const [manufacturerId, setManufacturerId] = useState(1);
    const [image, setImage] = useState<File | null>(null); // Add state for the image

    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append("name", name);
        formData.append("description", description);
        formData.append("price", price.toString());
        formData.append("units", units.toString());
        formData.append("categoryId", categoryId.toString());
        formData.append("manufacturerId", manufacturerId.toString());
        formData.append("discount", "0");

        // Append image if exists
        if (image) {
            formData.append("image", image);
        }

        try {
            await axios.post(`${BACKEND_PREFIX}/api/product/add`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
            navigate("/");
        } catch (err) {
            alert("Error adding product");
        }
    };

    return (
        <div className="container mt-5">
            <h2>Add Product</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Name</label>
                    <input className="form-control" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div className="mb-3">
                    <label>Description</label>
                    <input className="form-control" value={description} onChange={(e) => setDescription(e.target.value)} />
                </div>
                <div className="mb-3">
                    <label>Price</label>
                    <input type="number" className="form-control" value={price} onChange={(e) => setPrice(parseFloat(e.target.value))} required />
                </div>
                <div className="mb-3">
                    <label>Units</label>
                    <input type="number" className="form-control" value={units} onChange={(e) => setUnits(parseInt(e.target.value))} required />
                </div>
                <div className="mb-3">
                    <label>Category ID</label>
                    <input type="number" className="form-control" value={categoryId} onChange={(e) => setCategoryId(parseInt(e.target.value))} required />
                </div>
                <div className="mb-3">
                    <label>Manufacturer ID</label>
                    <input type="number" className="form-control" value={manufacturerId} onChange={(e) => setManufacturerId(parseInt(e.target.value))} required />
                </div>
                <div className="mb-3">
                    <label>Product Image</label>
                    <input type="file" className="form-control" onChange={(e) => setImage(e.target.files ? e.target.files[0] : null)} />
                </div>
                <button className="btn btn-primary" type="submit">Add Product</button>
            </form>
        </div>
    );
};

export default AddProduct;
