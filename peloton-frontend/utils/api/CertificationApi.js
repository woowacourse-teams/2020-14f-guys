import Axios from "axios";

import { SERVER_BASE_URL } from "../constants";

export const CertificationApi = {
  post: async (token, formData) => {
    const { headers } = await Axios.post(
      `${SERVER_BASE_URL}/api/certifications`,
      formData,
      {
        headers: {
          ContentType: "multipart/form-data",
          Authorization: `Bearer ${token}`,
        },
      },
    );
    return headers;
  },
};
