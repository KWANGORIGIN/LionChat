import React from 'react';
import Logo from './LionChat_LogoWhite.svg'
import Close from './LionChat_Close.svg'

export default function ChatHeader({ minimize }) {
    const containerStyle = {
        backgroundColor: 'rgb(71, 89, 252)',
        height: 40,
        display: 'flex',
        alignItems: 'center',
        color: 'white',
        fontSize: 25,
        fontFamily: 'Comic Sans MS'
    }

    const logoStyle = {
        margin: 10,
        height: 30,
        marginTop: 12
    }

    const closeStyle = {
        height: 20,
        marginRight: 10,
        marginLeft: 'auto',
        cursor: 'pointer'
    }

    return (
        <div style={containerStyle}>
            <img src={Logo} style={logoStyle} draggable='false' alt="LionChat Logo" />
            <label>LionChat</label>
            <img src={Close} style={closeStyle} onClick={minimize} draggable='false' alt="Close" />
        </div>
    );
}
