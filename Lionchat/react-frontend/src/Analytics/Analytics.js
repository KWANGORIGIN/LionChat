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
  const [averageRatings, setAverageRatings] = useState("No Ratings");
  const [
    numberMisclassificationsPerTopic,
    setNumberMisclassificationsPerTopic,
  ] = useState();
  const [numberInappropriateQueries, setNumberInappropriateQueries] =
    useState();
  //   const [inappropriateQueries, setInappropriateQueries] = useState();
  useEffect(() => {
    const fetchAnalytics = async () =>
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
          .then((body) =>
            body === -1
              ? setAverageRatings("No Ratings")
              : setAverageRatings(body)
          ),
        fetch("/administrative/number-misclassifications-per-topic")
          .then((response) => response.json())
          .then((body) => setNumberMisclassificationsPerTopic(body)),
        fetch("/administrative/number-inappropriate-queries")
          .then((response) => response.json())
          .then((body) => setNumberInappropriateQueries(body)),
        // fetch("/administrative/inappropriate-queries").then(response=>response.json()),
      ]);
    fetchAnalytics();
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
