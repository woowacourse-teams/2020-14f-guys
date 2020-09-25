import Axios from "axios";
import { SERVER_BASE_URL } from "../constants";
import { logApi } from "../Analytics";

export const MissionApi = {
  get: async (token, missionId) => {
    logApi("Mission", "Get");
    const response = await Axios({
      method: "GET",
      baseURL: SERVER_BASE_URL,
      url: `/api/missions/${missionId}`,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  },
};
