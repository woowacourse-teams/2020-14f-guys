import React, { useCallback, useEffect } from "react";
import { ScrollView, StyleSheet } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImages from "./RaceCertificationImages";
import RaceSpec from "./RaceSpec";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { COLOR } from "../../../utils/constants";
import { useRecoilState } from "recoil";
import { memberTokenState } from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { RaceApi } from "../../../utils/api/RaceApi";
import LinkCopyButton from "./LinkCopyButton";
import { RiderApi } from "../../../utils/api/RiderApi";
import { ridersInfoState } from "../../../state/rider/RiderState";
import { QueryApi } from "../../../utils/api/QueryApi";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { certificationState } from "../../../state/certification/CertificationState";
import { useFocusEffect } from "@react-navigation/core";

const RaceDetail = ({ route }) => {
  const newRaceId = route.params.id;
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const [ridersInfo, setRidersInfo] = useRecoilState(ridersInfoState);
  const [certificationInfo, setCertificationInfo] = useRecoilState(
    certificationState,
  );

  useEffect(() => {
    setIsLoading(true);
    const fetchRace = async () => {
      if (!newRaceId) {
        alert("잘못된 경로입니다.");
        setIsLoading(false);
        return;
      }
      const race = await RaceApi.get(token, newRaceId);
      const { rider_responses: riders } = await RiderApi.getInRace(
        token,
        newRaceId,
      );

      const { certifications } = await QueryApi.getRaceCertifications(
        token,
        newRaceId,
      );

      setRidersInfo(riders);
      setRaceInfo(race);
      setCertificationInfo(certifications.content);
      setIsLoading(false);
    };
    fetchRace();
  }, []);

  return (
    <LoadingIndicator>
      <ScrollView style={styles.container}>
        <RaceCertificationImages certifications={certificationInfo} />
        <RaceDetailInfo
          title={raceInfo.title}
          description={raceInfo.description}
        />
        <RaceSpec
          raceDuration={raceInfo.race_duration}
          cash={raceInfo.entrance_fee}
          riderCount={ridersInfo.length}
        />
        <LinkCopyButton raceId={newRaceId} />
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
