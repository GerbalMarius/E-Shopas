import {Link} from "react-router-dom";


const Home = () => {
    return (
        <div style={{}}>
            <Link to={"/product"}>Product</Link>
            <br/>
            <Link to={"/cart"}>Cart</Link>
            <br/>
            <Link to={"/register"}>Register</Link>
            <br/>
            <Link to={"/login"}>Login</Link>
        </div>
    )
}

export default Home;