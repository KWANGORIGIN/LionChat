import React from 'react';
import Logo from './LionChat_LogoWhite.svg'

const ChatBubble = ({ maximize }) => {
    const bubbleStyle = {
        width: 50,
        height: 50,
        borderRadius: '35%',
        position: 'fixed',
        background: 'rgb(71, 89, 252)',
        boxShadow: 'rgb(0 0 0 / 20%) 0px 0px 12px',
        userSelect: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        cursor: 'pointer',
        bottom: 10,
        right: 10,
        zIndex: 100000
    }

    const logoStyle = {
        width: 38
    }

    return (
        <div style={bubbleStyle} onClick={maximize} draggable='false'>
            <img src={Logo} style={logoStyle} draggable='false' alt='LionChat Logo' />
        </div >
    )
}

export default ChatBubble
