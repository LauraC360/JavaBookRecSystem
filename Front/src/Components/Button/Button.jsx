import { useEffect } from "react";
import "./Button.css";

export function Button(props) 
{

    const logout = () =>{
        console.log('props:', props);
        console.log('logging out');
        if(props.text === 'Sign Out'){
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


    };


    return (
        <button onClick={logout} >{props.text}</button>
    );
}