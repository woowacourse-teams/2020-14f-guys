import * as Amplitude from "expo-analytics-amplitude";

export const logApi = (category, method) => {
  Amplitude.logEvent(`API_${category}_${method}`);
};
