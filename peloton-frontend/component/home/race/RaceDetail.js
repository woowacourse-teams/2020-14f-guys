import React, { useEffect, useState } from "react";
import { ScrollView, StyleSheet } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImage from "./RaceCertificationImage";
import RaceSpec from "./RaceSpec";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { userTokenState } from "../../atoms";
import { loadingState } from "../../../state/loading/LoadingState";
import Axios from "axios";
import { COLOR, SERVER_BASE_URL } from "../../../utils/constants";

const calculateTotalCash = (cash) => {
  return cash * 10;
};

const RaceDetail = ({ route }) => {
  const token = useRecoilValue(userTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const raceId = route.params.location.split("/")[3];
  const [raceInfo, setRaceInfo] = useState({
    id: "",
    title: "",
    description: "",
    thumbnail: "",
    certification_example: "",
    race_duration: "",
    category: "",
    entrance_fee: "",
    time_duration: "",
  });

  useEffect(() => {
    const fetchRace = async () => {
      setIsLoading(true);
      const response = await Axios({
        method: "GET",
        baseURL: SERVER_BASE_URL,
        url: `api/races/${raceId}`,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setRaceInfo(response.data);
    };
    fetchRace();
    setIsLoading(false);
  }, []);

  return (
    <ScrollView style={styles.container}>
      <RaceCertificationImage />
      <RaceDetailInfo
        title={raceInfo.title}
        description={raceInfo.description}
      />
      <RaceSpec
        raceDuration={raceInfo.race_duration}
        cash={calculateTotalCash(parseInt(raceInfo.entrance_fee))}
      />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE,
  },
});

export default RaceDetail;
