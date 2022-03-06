import React,{useState, useEffect} from "react";
import { Button,InputGroup, FormControl } from "react-bootstrap";
import axios from "axios";

const User = () => {

    const [message, setMessage] = useState("");

    useEffect(() => {
        axios.get("/api/hello")
            .then((response) => {
                setMessage(response.data)
            });
    },[]);

    return(
        <div>
            {message}
            <InputGroup className="mb-3">
                <InputGroup.Text id="inputGroup-sizing-default">Username</InputGroup.Text>
                <FormControl
                    aria-label="Default"
                    aria-describedby="inputGroup-sizing-default"
                />
            </InputGroup>
            <InputGroup className="mb-3">
                <InputGroup.Text id="inputGroup-sizing-default">Password</InputGroup.Text>
                <FormControl
                    aria-label="Default"
                    aria-describedby="inputGroup-sizing-default"
                />
            </InputGroup>
        </div>
    )

};

export default User;