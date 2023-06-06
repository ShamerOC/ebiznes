import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {Cart} from "../hooks/useCart";

interface Product {
    id: number;
    name: string;
    category: string;
    price: number;
}

function Products(cart: Cart) {
    const [products, setProducts] = useState<Product[]>([]);

    const { addItem, items } = cart;
    console.log("items " + items);

    useEffect(() => {
        axios.get<Product[]>('http://localhost:1323/products')
            .then(res => setProducts(res.data))
            .catch(err => console.log(err));
    }, []);

    return (
        <div>
            <h1>Lista produktów:</h1>
    <ul className='products'>
    {products.map(product => (
            <li key={product.id}>
                <h2>{product.name}</h2>
                <p>{product.category}</p>
                <p>Cena: {product.price} zł</p>
                <button onClick={() => addItem({id: product.id, name: product.name, price: product.price})}>Add to cart</button>
            </li>

))}
    </ul>
    </div>
);
}

export default Products;
