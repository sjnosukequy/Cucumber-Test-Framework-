export default {
  name: "Automation Exercise Allure Report",
  output: "./reports/allure",
  plugins: {
    awesome: {
      enabled: true,
      options: {
        reportName: "Allure 3 version",
        singleFile: false,
        reportLanguage: "en",
        // groupBy: ["epic", "feature", "story"],
      },
    },
    dashboard: {
      enabled: true,
      options: { reportName: "Allure Dashboard version", reportLanguage: "en" },
    },
    allure2: {
      options: {
        reportName: "Allure 2 version",
        singleFile: false,
        reportLanguage: "en",
      },
    },
  },
  variables: {},
  environments: {},
};
