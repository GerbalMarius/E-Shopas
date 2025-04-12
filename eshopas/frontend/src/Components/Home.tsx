import {Link, NavigateFunction, useNavigate} from "react-router-dom";
import {
    Navbar,
    NavbarBrand,
    NavbarToggler,
    Collapse,
    Nav,
    NavItem,
    NavLink
} from "reactstrap";
import React, {useEffect} from "react";
import axios from "axios";
import {BACKEND_PREFIX} from "../App";

interface Category {
    id : bigint
    description : string
    title : string
}

const Home = () => {
    const [isOpen, setIsOpen] = React.useState(false);
    const toggle = () => setIsOpen(!isOpen);

    const navigate: NavigateFunction = useNavigate();

    const [categories, setCategories] = React.useState<Category[] | null>([]);

    useEffect(() => {
        const contr = new AbortController();

        (async () => {
            try {
                const response = await axios.get<Category[]>(
                    `${BACKEND_PREFIX}/api/product/categories`,
                    { signal: contr.signal }
                );
                setCategories(response.data);
            } catch (err) {
                if (!contr.signal.aborted) {
                    setCategories([]); // set to empty array or null as fallback
                    navigate("/");
                }
            }
        })();

        return () => contr.abort();
    }, [navigate]);
    const actualCategories = categories ?? []
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
                        <h2>Categories</h2>
                        <div className="category-grid">
                            {actualCategories.length > 0 ? (
                                actualCategories.map((category) => (
                                    <div key={category.id.toString()} className="category-card">
                                        <Link to={`/category/${category.id}`} className="text-decoration-none text-dark">
                                            <h5>{category.title}</h5>
                                            <p>{category.description}</p>
                                        </Link>
                                    </div>
                                ))
                            ) : (
                                <p>No categories available.</p>
                            )}
                        </div>
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