import React from 'react'
import styles from './AnalyticsHeader.module.css'

const AnalyticsHeader = () => {
    return (
        <div className={styles.header}>
            <a className={styles.headerLeft} >LionChat </a>
            <a className={styles.headerRight} >Analytics</a>
        </div>
    )
}

export default AnalyticsHeader