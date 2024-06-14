import './SignInContent.css';
import React, { useState } from 'react'; 
import axios from 'axios'; 
import { useNavigate } from 'react-router-dom'; 


export const Content = () => 
{
    const [username, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const user={username, password}
        console.log(user)   
        fetch("http://localhost:8082/api/v1/public/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify(user)
        }).then(response=>{
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('username or password is incorrect!')
            }
        }).then(data => {
            localStorage.setItem('jwtToken', data.token);
            console.log(data.token);
            console.log('User logged in successfully');
            navigate('/');
        }).catch(error => {
            setErrorMessage(error.message);
        });
    }

    return (
        <main className='sign-in-main'>
            <div className="component-1">
                <img src="img/ico/Lock.png" alt="img" />
                <form className='sign-in-form' onSubmit={handleSubmit}>
                    <input type="username" name='username' placeholder="Username" onChange={e => setEmail(e.target.value)} required/>
                    <input type="password" name='password' placeholder="Password" onChange={e => setPassword(e.target.value)} required/>
                    <button type="submit">Sign In</button>
                </form>
                <hr />
                <p className='acc-create'> <a href="/#/signup">Sign Up</a></p>
            </div>
        </main>
    )
}