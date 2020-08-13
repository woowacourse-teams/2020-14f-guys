import React, { useEffect } from "react";
import { ScrollView, StyleSheet } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImage from "./RaceCertificationImage";
import RaceSpec from "./RaceSpec";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { COLOR } from "../../../utils/constants";
import { useRecoilState } from "recoil/dist";
import { memberTokenState } from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { RaceApi } from "../../../utils/api/RaceApi";
import LinkCopyButton from "./LinkCopyButton";

const calculateTotalCash = (cash) => {
  return cash * 10;
};

const RaceDetail = ({ route }) => {
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const raceId = route.params.location.split("/")[3];
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);

  useEffect(() => {
    const fetchRace = async () => {
      setIsLoading(true);
      const response = await RaceApi.get(raceId, token);
      setRaceInfo(response);
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
      <LinkCopyButton raceId={raceId} />
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
