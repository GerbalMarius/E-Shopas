import React from "react";
import {
    Form,
    FormGroup,
    Label,
    Input
} from "reactstrap";

const Login = () => {
    return (
        <div className="purple-form">
            <h3 style={{ textAlign: "center", marginBottom: "25px", color: "#5f55d3" }}>
                Login
            </h3>
            <Form>
                <FormGroup>
                    <Label for="username">Username</Label>
                    <Input type="text" id="username" placeholder="Enter username" className="form-control" />
                </FormGroup>
                <FormGroup>
                    <Label for="password">Password</Label>
                    <Input type="password" id="password" placeholder="Enter password" className="form-control" />
                </FormGroup>
                <Input className="btn-purple-gradient" style={{color:"white"}} type={"submit"} value={"Login"}></Input>
            </Form>
        </div>
    );
};
export default Login;