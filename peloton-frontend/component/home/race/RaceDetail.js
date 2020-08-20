import React, { useEffect } from "react";
import { ScrollView, StyleSheet } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImages from "./RaceCertificationImages";
import RaceSpec from "./RaceSpec";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { COLOR } from "../../../utils/constants";
import { memberTokenState } from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import LinkCopyButton from "./LinkCopyButton";
import { RiderApi } from "../../../utils/api/RiderApi";
import { ridersInfoState } from "../../../state/rider/RiderState";
import { QueryApi } from "../../../utils/api/QueryApi";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { certificationsState } from "../../../state/certification/CertificationState";
import { RaceApi } from "../../../utils/api/RaceApi";

const RaceDetail = ({ route }) => {
  const raceId = route.params.id;
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const [ridersInfo, setRidersInfo] = useRecoilState(ridersInfoState);
  const [certificationsInfo, setCertificationsInfo] = useRecoilState(
    certificationsState
  );

  useEffect(() => {
    setIsLoading(true);
    const fetchRace = async () => {
      if (!raceId) {
        alert("잘못된 경로입니다.");
        setIsLoading(false);
        return;
      }
      const race = await RaceApi.get(token, raceId);
      const { rider_responses: riders } = await RiderApi.getInRace(
        token,
        raceId
      );

      const { certifications } = await QueryApi.getRaceCertifications(
        token,
        raceId
      );

      setRidersInfo(riders);
      setRaceInfo(race);
      setCertificationsInfo(certifications.content);
      setIsLoading(false);
    };
    fetchRace();
  }, []);

  return (
    <LoadingIndicator>
      <ScrollView style={styles.container}>
        <RaceCertificationImages />
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
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE,
  },
});

export default RaceDetail;
