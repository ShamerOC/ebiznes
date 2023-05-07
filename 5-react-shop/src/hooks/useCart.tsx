import { useState, useEffect } from 'react';

export interface CartItem {
    id: number;
    name: string;
    price: number;
}

export interface Cart {
    items: CartItem[];
    addItem: (item: CartItem) => void;
    removeItem: (id: number) => void;
    clearCart: () => void;
    itemCount: number;
    totalPrice: number;
}

const useCart = (): Cart => {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);

    useEffect(() => {
        // Load cart items from localStorage
        const storedItems = localStorage.getItem('cartItems');
        if (storedItems) {
            setCartItems(JSON.parse(storedItems));
        }
    }, []);

    useEffect(() => {
        // Save cart items to localStorage
        localStorage.setItem('cartItems', JSON.stringify(cartItems));
    }, [cartItems]);

    const addItem = (item: CartItem) => {
        setCartItems([...cartItems, item]);
        console.log("adding" + item.name);
    };

    const removeItem = (id: number) => {
        setCartItems(cartItems.filter((item) => item.id !== id));
    };

    const clearCart = () => {
        setCartItems([]);
    };

    const itemCount = cartItems.reduce((count, item) => count + 1, 0);

    const totalPrice = cartItems.reduce((total, item) => total + item.price, 0);

    return {
        items: cartItems,
        addItem,
        removeItem,
        clearCart,
        itemCount,
        totalPrice,
    };
};

export default useCart;
