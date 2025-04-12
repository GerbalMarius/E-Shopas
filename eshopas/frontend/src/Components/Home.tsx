import { Link } from "react-router-dom";
import {
    Navbar,
    NavbarBrand,
    NavbarToggler,
    Collapse,
    Nav,
    NavItem,
    NavLink
} from "reactstrap";
import React from "react";

const Home = () => {
    const [isOpen, setIsOpen] = React.useState(false);
    const toggle = () => setIsOpen(!isOpen);

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
                    {[1, 2, 3].map((item) => (
                        <Link key={item} to={`/product/${item}`} className="text-decoration-none text-dark">
                            <div className="product-card">
                                <img src={`/product${item}.jpg`} alt={`Product ${item}`} />
                                <h5>Product {item}</h5>
                                <p>$19.99</p>
                            </div>
                        </Link>
                    ))}
                </section>
            </div>
            </div>

            <footer style={{ padding: '20px', textAlign: 'center', background: '#eee' }}>
                © 2025 E-Shop
            </footer>
        </>
    );
};

export default Home;