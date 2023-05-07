import React from 'react';
import {Cart} from '../hooks/useCart';

type CartItemProps = {
    item: {
        id: number;
        name: string;
        price: number;
    };
    onRemove: (id: number) => void;
};

const CartItem = ({ item, onRemove }: CartItemProps) => {
    return (
        <li>
            {item.name} - ${item.price}
            <button onClick={() => onRemove(item.id)}>Remove</button>
        </li>
    );
};

const CartComponent = (cart: Cart) => {
    const { items, removeItem, clearCart, itemCount, totalPrice } = cart;

    console.log("items " + items);

    if (items.length === 0) {
        return <p>Your cart is empty.</p>;
    }

    return (
        <>
            <h2>Your Cart</h2>
            <ul>
                {items.map((item) => (
                    <CartItem key={item.id} item={item} onRemove={removeItem} />
                ))}
            </ul>
            <p>Item count: {itemCount}</p>
            <p>Total price: ${totalPrice}</p>
            <button onClick={clearCart}>Clear Cart</button>
        </>
    );
};

export default CartComponent;
