import React, { useEffect, useState } from "react";
import styles from "./Analytics.module.css";
import AnalyticsHeader from "./AnalyticsHeader";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Doughnut, Pie } from "react-chartjs-2";
ChartJS.register(ArcElement, Tooltip, Legend);

const chartOptions = {
  backgroundColor: [
    "#0e0e5d",
    "#000088",
    "#034c80",
    "#44aaff",
    "#77aaff",
    "#a6ced9",
    "#88ccff",
  ]
    .map((a) => ({ sort: Math.random(), value: a }))
    .sort((a, b) => a.sort - b.sort)
    .map((a) => a.value),
  borderColor: [],
  borderWidth: 1,
};

const Analytics = () => {
  // const [crashReports, setCrashReports] = useState();
  const [totalQuestionsAsked, setTotalQuestionsAsked] = useState();
  //   const [questionsAsked, setQuestionsAsked] = useState();
  const [numberQuestionsPerTopic, setNumberQuestionsPerTopic] = useState();
  const [averageRatings, setAverageRatings] = useState();
  const [averageRatingsPerTopic, setAverageRatingsPerTopic] = useState();
  const [
    numberMisclassificationsPerTopic,
    setNumberMisclassificationsPerTopic,
  ] = useState();
  const [numberInappropriateQueries, setNumberInappropriateQueries] =
    useState();
  //   const [inappropriateQueries, setInappropriateQueries] = useState();
  useEffect(async () => {
    Promise.all([
      // fetch("/administrative/crashreports"),
      fetch("/administrative/total-questions-asked")
        .then((response) => response.json())
        .then((body) => setTotalQuestionsAsked(body)),
      // fetch("/administrative/questions-asked").then(response=>response.json()),
      fetch("/administrative/number-questions-per-topic")
        .then((response) => response.json())
        .then((body) => setNumberQuestionsPerTopic(body)),
      fetch("/administrative/average-ratings")
        .then((response) => response.json())
        .then((body) => setAverageRatings(body)),
      fetch("/administrative/average-ratings-per-topic")
        .then((response) => response.json())
        .then((body) => setAverageRatingsPerTopic(body)),
      fetch("/administrative/number-misclassifications-per-topic")
        .then((response) => response.json())
        .then((body) => setNumberMisclassificationsPerTopic(body)),
      fetch("/administrative/number-inappropriate-queries")
        .then((response) => response.json())
        .then((body) => setNumberInappropriateQueries(body)),
      // fetch("/administrative/inappropriate-queries").then(response=>response.json()),
    ]);
  }, []);

  useEffect(() => {}, [numberQuestionsPerTopic]);

  return (
    <>
      <AnalyticsHeader />
      <ul className={styles.analyticsList}>
        <li>Crash Reports: Not Implemented</li>
        <li>Total Questions Asked: {totalQuestionsAsked}</li>
        <li>Questions Asked: Not Implemented</li>
        {/* <Doughnut data={numberQuestionsPerTopic} /> */}
        <li>Number Questions Per Topic:</li>
        {numberQuestionsPerTopic && (
          <div
            style={{
              position: "relative",
              height: "20rem",
              width: "20rem",
            }}
          >
            <Doughnut
              data={{
                labels: Object.keys(numberQuestionsPerTopic),
                datasets: [
                  {
                    label: "# Questions Asked",
                    data: Object.values(numberQuestionsPerTopic),
                    ...chartOptions,
                  },
                ],
              }}
            />
          </div>
        )}
        <li>Average Ratings: {averageRatings}</li>
        {/* <li>
          Average Ratings Per Topic:{" "}
          {averageRatingsPerTopic && (
            <div
              style={{
                position: "relative",
                height: "20rem",
                width: "20rem",
              }}
            >
              <Doughnut
                data={{
                  labels: Object.keys(averageRatingsPerTopic),
                  datasets: [
                    {
                      label: "# Questions Asked",
                      data: Object.values(averageRatingsPerTopic),
                    },
                  ],
                }}
              />
            </div>
          )}
        </li> */}
        <li>
          Number Misclassifications Per Topic:{" "}
          {numberMisclassificationsPerTopic && (
            <div
              style={{
                position: "relative",
                height: "20rem",
                width: "20rem",
              }}
            >
              <Doughnut
                data={{
                  labels: Object.keys(numberMisclassificationsPerTopic),
                  datasets: [
                    {
                      label: "# Questions Asked",
                      data: Object.values(numberMisclassificationsPerTopic),
                      ...chartOptions,
                    },
                  ],
                }}
              />
            </div>
          )}
        </li>
        <li>Number Inappropriate Queries: {numberInappropriateQueries}</li>
        <li>Inappropriate Queries: Not Implemented</li>
      </ul>
    </>
  );
};

export default Analytics;
