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
import { RiderApi } from "../../../utils/api/RiderApi";
import { ridersInfoState } from "../../../state/rider/RiderState";

const RaceDetail = ({ route }) => {
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const raceId = route.params.location.split("/")[3];
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const [ridersInfo, setRidersInfo] = useRecoilState(ridersInfoState);

  useEffect(() => {
    const fetchRace = async () => {
      setIsLoading(true);
      const race = await RaceApi.get(token, raceId);
      const { rider_responses: riders } = await RiderApi.getInRace(
        token,
        raceId
      );
      setRidersInfo(riders);
      setRaceInfo(race);
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
        cash={raceInfo.entrance_fee}
        riderCount={ridersInfo.length}
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
