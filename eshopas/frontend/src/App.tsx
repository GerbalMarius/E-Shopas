import React from 'react';
import {Route, BrowserRouter as ReactRouter, Routes} from "react-router-dom";
import Home from "./Components/Home";
import Product from "./Components/Product";
import Cart from "./Components/Cart";
import Order from "./Components/Order";
import Login from "./Components/Login";
import Register from "./Components/Register";
import 'bootstrap/dist/css/bootstrap.min.css';
 import './index.css';

export interface CartItem {
    name: string;
    price: number;
    quantity: number;
}

export const BACKEND_PREFIX:string = 'http://localhost:8080';
function App()  {
    return (
        <div className="page-container">
            <ReactRouter>{}
                <div>
                    <main>
                        <Routes>
                            <Route path="/"  element={<Home />} />
                            <Route path={"/product/:id"} element={<Product/>} />
                            <Route path={"/cart"} element={<Cart/>}></Route>
                            <Route path={"/order"} element={<Order/>}></Route>
                            <Route path={"/login"} element={<Login/>}></Route>
                            <Route path={"/register"} element={<Register/>}></Route>
                        </Routes>
                    </main>
                </div>
            </ReactRouter>
        </div>
    );
}

export default App;
