import React, {useState} from "react";
import axios from 'axios';

const Register = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();

        // Perform registration logic here (e.g., send data to backend)
        console.log('Register:', firstName, password);

        axios.post('http://localhost:8080/api/v1/auth/register', {
            "firstName": firstName,
            "lastName": lastName,
            "email": email,
            "password": password
        })
            .then(function (response) {
                console.log(response);
                alert("Successfully registered! Login with " + email + " and " + password + ".");

                setFirstName('');
                setLastName('');
                setEmail('');
                setPassword('');
            })
    };

    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    First Name:
                    <input type="text" value={firstName} onChange={(event) => setFirstName(event.target.value)} />
                </label>
                <br />
                <label>
                    Last Name:
                    <input type="text" value={lastName} onChange={(event) => setLastName(event.target.value)} />
                </label>
                <br />
                <label>
                    Email:
                    <input type="text" value={email} onChange={(event) => setEmail(event.target.value)} />
                </label>
                <br />
                <label>
                    Password:
                    <input type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
                </label>
                <br />
                <button type="submit">Register</button>
            </form>
        </div>
    );
};

export default Register;
