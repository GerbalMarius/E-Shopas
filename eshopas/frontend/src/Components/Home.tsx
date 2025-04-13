import { Link, NavigateFunction, useNavigate } from "react-router-dom";
import { Navbar, NavbarBrand, NavbarToggler, Collapse, Nav, NavItem, NavLink } from "reactstrap";
import React, { useEffect } from "react";
import axios from "axios";
import { BACKEND_PREFIX } from "../App";

interface ProductView {
    id: number;
    name: string;
    price: number;
    pictureBase64?: string;
}

interface Category {
    id: number;
    title: string;
}

interface Manufacturer {
    id: number;
    name: string;
}

const Home = () => {
    const [isOpen, setIsOpen] = React.useState(false);
    const [categoryFilter, setCategoryFilter] = React.useState<string>('');
    const [manufacturerFilter, setManufacturerFilter] = React.useState<string>('');
    const [filteredProducts, setFilteredProducts] = React.useState<ProductView[] | null>(null);
    const [categories, setCategories] = React.useState<Category[]>([]);
    const [manufacturers, setManufacturers] = React.useState<Manufacturer[]>([]);
    const [maxPrice, setMaxPrice] = React.useState<number>(10000);

    const toggle = () => setIsOpen(!isOpen);
    const navigate: NavigateFunction = useNavigate();

    const [products, setProducts] = React.useState<ProductView[] | null>([]);

    useEffect(() => {
        const contr = new AbortController();

        // Fetch all products initially
        (async () => {
            try {
                const response = await axios.get<ProductView[]>(
                    `${BACKEND_PREFIX}/api/product/products`,
                    { signal: contr.signal }
                );
                setProducts(response.data);
                setFilteredProducts(response.data);  // Set filteredProducts to all products initially
            } catch (err) {
                if (!contr.signal.aborted) {
                    setProducts([]);
                    setFilteredProducts([]);
                    navigate("/");
                }
            }
        })();

        // Fetch categories and manufacturers for the filter options
        (async () => {
            try {
                const categoryResponse = await axios.get<Category[]>(
                    `${BACKEND_PREFIX}/api/product/categories`,
                    {withCredentials: true}
                );
                setCategories(categoryResponse.data);

                const manufacturerResponse = await axios.get<Manufacturer[]>(
                    `${BACKEND_PREFIX}/api/product/manufacturers`,
                    {withCredentials: true}
                );
                setManufacturers(manufacturerResponse.data);
            } catch (err) {
                console.error("Error fetching categories or manufacturers:", err);
            }
        })();

        return () => contr.abort();
    }, [navigate]);

    const filterProducts = async () => {
        const filters: Record<string, any> = {};

        filters.minPrice = 0; // visada nuo 0
        if (maxPrice !== null) filters.maxPrice = maxPrice;
        if (categoryFilter) filters.category = categoryFilter;
        if (manufacturerFilter) filters.manufacturer = manufacturerFilter;

        try {
            const response = await axios.get<ProductView[]>(`${BACKEND_PREFIX}/api/product/products`, { params: filters });
            setFilteredProducts(response.data);
        } catch (error) {
            console.error("Error fetching filtered products:", error);
            setFilteredProducts([]);
        }
    };
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

                    <div className="content-with-sidebar">
                        <aside className="filters">
                            <div className="filter">
                                <label>Price: 0 - {maxPrice}€</label>
                                <input
                                    type="range"
                                    min="0"
                                    max="10000"
                                    step="10"
                                    value={maxPrice}
                                    onChange={(e) => setMaxPrice(Number(e.target.value))}
                                />
                            </div>

                            <div className="filter">
                                <label>Category:</label>
                                <select onChange={(e) => setCategoryFilter(e.target.value)} value={categoryFilter}>
                                    <option value="">All</option>
                                    {categories.map((category) => (
                                        <option key={category.id} value={category.id}>{category.title}</option>
                                    ))}
                                </select>
                            </div>

                            <div className="filter">
                                <label>Manufacturer:</label>
                                <select onChange={(e) => setManufacturerFilter(e.target.value)} value={manufacturerFilter}>
                                    <option value="">All</option>
                                    {manufacturers.map((manufacturer) => (
                                        <option key={manufacturer.id} value={manufacturer.id}>{manufacturer.name}</option>
                                    ))}
                                </select>
                            </div>

                            <button onClick={filterProducts} className="btn btn-primary">Filter</button>
                        </aside>

                        <section className="product-grid">
                            {(filteredProducts ?? []).length > 0 ? (
                                (filteredProducts ?? []).map((product) => (
                                    <Link key={product.id.toString()} to={`/product/${product.id}`} className="text-decoration-none text-dark">
                                        <div className="product-card">
                                            <img
                                                src={product.pictureBase64 ? `data:image/jpeg;base64,${product.pictureBase64}` : '/default-product.jpg'}
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
            </div>
            <h2>Products</h2>
            <Link to="/add-product">
                <button className="btn btn-primary">Add Product</button>
            </Link>

            <footer style={{ padding: '20px', textAlign: 'center', background: '#eee' }}>
                © 2025 E-Shop
            </footer>
        </>
    );
};

export default Home;
