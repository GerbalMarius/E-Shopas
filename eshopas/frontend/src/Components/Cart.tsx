import {Link} from "react-router-dom";


const Cart = () => {

    return (
        <div >
            <h1>This is current cart</h1>
            <p></p>
            <Link to={"/order"}>Submit order.</Link>
        </div>
    )
}
export default Cart;