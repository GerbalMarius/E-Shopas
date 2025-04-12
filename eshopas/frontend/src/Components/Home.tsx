import { Link, NavigateFunction, useNavigate } from "react-router-dom";
import {
    Navbar,
    NavbarBrand,
    NavbarToggler,
    Collapse,
    Nav,
    NavItem,
    NavLink
} from "reactstrap";
import React, { useEffect } from "react";
import axios from "axios";
import { BACKEND_PREFIX } from "../App";

interface Product {
    id: number; // Using number instead of bigint to avoid issues
    name: string;
    price: number;
    pictureBase64?: string; // Base64 encoded picture string
}

const Home = () => {
    const [isOpen, setIsOpen] = React.useState(false);
    const toggle = () => setIsOpen(!isOpen);

    const navigate: NavigateFunction = useNavigate();

    const [products, setProducts] = React.useState<Product[] | null>([]);

    useEffect(() => {
        const contr = new AbortController();

        (async () => {
            try {
                const response = await axios.get<Product[]>(
                    `${BACKEND_PREFIX}/api/product/products`,
                    { signal: contr.signal }
                );
                setProducts(response.data);
            } catch (err) {
                if (!contr.signal.aborted) {
                    setProducts([]); // set to empty array or null as fallback
                    navigate("/");
                }
            }
        })();

        return () => contr.abort();
    }, [navigate]);

    const actualProducts = products ?? [];

    return (
        <>
            <Navbar className="navbar-custom" expand="md">
                <NavbarBrand href="/">E-Shop</NavbarBrand>
                <NavbarToggler onClick={toggle} />
                <Collapse isOpen={isOpen} navbar>
                    <Nav className="ms-auto d-flex align-items-center" navbar>
                        <NavItem className="me-3">
                            <NavLink tag={Link} to="/cart" className="text-light">
                                <img
                                    src="/cart-shopping-svgrepo-com.svg"
                                    alt="Cart"
                                    className="cart-icon"
                                />
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={Link} to="/register">
                                <button className="btn btn-outline-light me-2">Register</button>
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={Link} to="/login">
                                <button className="btn btn-outline-light me-2">Login</button>
                            </NavLink>
                        </NavItem>
                    </Nav>
                </Collapse>
            </Navbar>

            <div className="page-wrapper">
                <div className="page-content home-container">
                    <section className="hero">
                        <h1>Welcome to E-Shop</h1>
                        <p>Discover amazing products and exclusive deals.</p>
                    </section>

                    <section className="product-grid">
                        {actualProducts.length > 0 ? (
                            actualProducts.map((product) => (
                                <Link key={product.id.toString()} to={`/product/${product.id}`} className="text-decoration-none text-dark">
                                    <div className="product-card">
                                        <img
                                            src={
                                                product.pictureBase64
                                                    ? `data:image/jpeg;base64,${product.pictureBase64}`
                                                    : '/default-product.jpg'
                                            }
                                            alt={product.name}
                                            className="product-image"
                                        />
                                        <h5>{product.name}</h5>
                                        <p>{product.price.toFixed(2)}€</p>
                                    </div>
                                </Link>
                            ))
                        ) : (
                            <p>No products available.</p>
                        )}
                    </section>
                </div>
            </div>
            <h2>Products</h2>
            <Link to="/add-product">
                <button className="btn btn-success mb-3">Add Product</button>
            </Link>

            <footer style={{ padding: '20px', textAlign: 'center', background: '#eee' }}>
                © 2025 E-Shop
            </footer>
        </>
    );
};

export default Home;
