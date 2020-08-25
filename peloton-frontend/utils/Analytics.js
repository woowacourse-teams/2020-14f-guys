import * as Amplitude from "expo-analytics-amplitude";

export const logApi = (category, method) => {
  Amplitude.logEvent(`API_${category}_${method}`);
};

export const logNav = (tabName, stackName) => {
  Amplitude.logEvent(`NAV_${tabName}_${stackName}`);
};
