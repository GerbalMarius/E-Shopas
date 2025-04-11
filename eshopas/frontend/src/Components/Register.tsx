

import React from "react";
import {
    Form,
    FormGroup,
    Label,
    Input,
    Button
} from "reactstrap";

const Register = () => {

    return (
        <div className="purple-form">
            <h3 style={{ textAlign: "center", marginBottom: "25px", color: "#5f55d3" }}>
                Create Your Account
            </h3>
            <Form>
                <FormGroup>
                    <Label for="firstName">First Name</Label>
                    <Input type="text" id="firstName" placeholder="Enter first name" className="form-control" />
                </FormGroup>

                <FormGroup>
                    <Label for="lastName">Last Name</Label>
                    <Input type="text" id="lastName" placeholder="Enter last name" className="form-control" />
                </FormGroup>

                <FormGroup>
                    <Label for="email">Email</Label>
                    <Input type="email" id="email" placeholder="Enter email address" className="form-control" />
                </FormGroup>

                <FormGroup>
                    <Label for="password">Password</Label>
                    <Input type="password" id="password" placeholder="Create a password" className="form-control" />
                </FormGroup>

                <FormGroup>
                    <Label for="telephoneNumber">Telephone Number</Label>
                    <Input type="tel" id="telephoneNumber" placeholder="Enter your phone number" className="form-control" />
                </FormGroup>

                <FormGroup>
                    <Label for="gender">Gender</Label>
                    <Input type="select" id="gender" className="form-control">
                        <option value="">Select gender</option>
                        <option>Male</option>
                        <option>Female</option>
                    </Input>
                </FormGroup>

                <Button className="btn-purple-gradient">Register</Button>
            </Form>
        </div>
    );
};

export default Register;