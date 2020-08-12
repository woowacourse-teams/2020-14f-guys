import React, { useEffect } from "react";
import { ScrollView, StyleSheet } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImage from "./RaceCertificationImage";
import RaceSpec from "./RaceSpec";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import Axios from "axios";
import { COLOR, DEEP_LINK_BASE_URL, SERVER_BASE_URL } from "../../../utils/constants";
import { useRecoilState } from "recoil/dist";
import { memberTokenState } from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";

const calculateTotalCash = (cash) => {
  return cash * 10;
};

const RaceDetail = ({ route }) => {
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const raceId = route.params.location.split("/")[3];
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);

  useEffect(() => {
    if(!raceInfo) {
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
    }
    console.log(raceInfo);
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
        url={`${DEEP_LINK_BASE_URL}races/${raceId}`}
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
