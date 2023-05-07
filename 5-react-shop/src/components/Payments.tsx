import React from 'react';
import axios from 'axios';

interface PaymentsProps {
    amount: number;
}

function Payments({ amount }: PaymentsProps) {
    const handlePayNowClick = () => {
        axios.post('http://localhost:1323/pay', {
            amount: amount,
        })
            .then(response => {
                console.log(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    };

    return (
        <div>
            <h2>Payments</h2>
            <p>Amount: ${amount}</p>
            <button onClick={handlePayNowClick}>Pay Now</button>
        </div>
    );
}

export default Payments;
