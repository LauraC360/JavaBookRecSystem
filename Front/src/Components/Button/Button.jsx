import { useEffect } from "react";
import "./Button.css";

export function Button(props) 
{

    useEffect(() => {
        if(props === 'Sign Out'){
            try{
            const response = fetch(`http://localhost:8082/api/v1/logout`,
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                }
            });

            if(response.ok){
                console.log('User has been logged out');
            }
        } catch (error) {
            console.log('Error:', error);
        }  

        
        }


    }, []);


    return (
        <button>{props.text}</button>
    );
}