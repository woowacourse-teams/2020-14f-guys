import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";

export const CalculationApi = {
  post: async (token, raceId) => {
    await Axios({
      method: "POST",
      baseURL: SERVER_BASE_URL,
      url: `/api/calculations/races/${raceId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  },
  get: async (token, raceId) => {
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: `/api/calculations/races/${raceId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
};
