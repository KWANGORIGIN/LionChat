import React, { useState } from "react";
import Logo from "../LionChat_Logo.svg";
import styles from './ReviewMessage.module.css'
import { Rating } from '@mui/material';

const ReviewMessage = ({ text, id, reviewId, score, handleSendReview }) => {
    const sendReview = async (newScore) => {
        const oldScore = score
        const reviewRequest = { "reviewId": reviewId, "review": newScore }

        const message = { "key": id, "score": newScore }
        handleSendReview(message)

        const response = await fetch(`/chat/update-review`,
            {
                method: 'PUT',
                mode: 'cors',
                cache: 'no-cache',
                credentials: 'same-origin',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reviewRequest),
            })

        if (!response?.ok) {
            const message = { "key": id, "score": oldScore }
            handleSendReview(message)
        }
    }

    return (
        <div className={styles.container}>
            <img
                src={Logo}
                className={styles.logo}
                draggable="false"
                alt="LionChat Logo"
            />
            <div className={styles.messageBubble}>
                <div className={styles.reviewMessage}>
                    <label>{text}</label>
                    <br />
                    <Rating
                        name="simple-controlled"
                        value={score}
                        onChange={(event, newScore) => {
                            if (!newScore) {
                                event.preventDefault();
                            } else {
                                sendReview(newScore);
                            }
                        }}
                    />
                </div>
            </div>
        </div >
    );
};

ReviewMessage.propTypes = {};

export default ReviewMessage;
