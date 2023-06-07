import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Link, Routes} from 'react-router-dom';
import Register from "./components/Register";
import Login from "./components/Login";

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleLogout = () => {
        setIsLoggedIn(false);
    };

    return (
        <Router>
            <nav>
                <ul>
                    <li>
                        <Link to="/register">Register</Link>
                    </li>
                    <li>
                        <Link to="/login">Login</Link>
                    </li>
                    {isLoggedIn && (
                        <li>
                            <button onClick={handleLogout}>Logout</button>
                        </li>
                    )}
                </ul>
            </nav>

            <Routes>
                <Route path="/register" element={<Register />}/>
                <Route
                    path="/login"
                    element={<Login />}
                    // render={() => (isLoggedIn ? "" : <Login handleLogin={handleLogin}/>)}
                />
            </Routes>
        </Router>
    );
};

export default App;
