import {useState} from "react";
import axios from "axios";

const Chat = () => {
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);

    const handleInputChange = event => {
        setMessage(event.target.value);
    };

    const handleSubmit = event => {
        event.preventDefault();
        if (message.trim() !== '') {
            axios.get('http://localhost:8080/chat?message=' + message)
                .then(response => {
                    setMessages(() => [...messages, message, response.data["response"]]);
                })
                .catch(error => { console.log(error) });
        }
    };

    return (
        <div className="chat-app">
            <h1>Chat Application</h1>
            <div className="chat-container">
                <div className="message-list">
                    {messages.map((msg, index) => (
                        <div key={index} className="message">{msg}</div>
                    ))}
                </div>
                <form className="message-form" onSubmit={handleSubmit}>
                    <input
                        type="text"
                        placeholder="Type your message..."
                        value={message}
                        onChange={handleInputChange}
                    />
                    <button type="submit">Send</button>
                </form>
            </div>
        </div>
    );
};


export default Chat;
