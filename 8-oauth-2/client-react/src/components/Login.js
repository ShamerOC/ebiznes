import {useState} from "react";
import axios from "axios";

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();

        // Perform login logic here (e.g., send data to backend)
        console.log('Login:', email, password);

        axios.post('http://35.158.93.93:8080/api/v1/auth/authenticate', {
            "email": email,
            "password": password
        })
            .then(function (response) {
                console.log(response);
                alert("Successfully logged in!");

                setEmail('');
                setPassword('');
            })
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Username:
                    <input type="text" value={email} onChange={(event) => setEmail(event.target.value)} />
                </label>
                <br />
                <label>
                    Password:
                    <input type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
                </label>
                <br />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;
