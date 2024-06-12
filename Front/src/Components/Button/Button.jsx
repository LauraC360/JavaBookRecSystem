import { useEffect } from "react";
import "./Button.css";

export function Button(props) 
{

    useEffect(() => {
        if(props === 'Sign out'){}
    }, []);

    return (
        <button>{props.text}</button>
    );
}