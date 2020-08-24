import React, { useEffect } from "react";
import { Alert, ScrollView, StyleSheet, View } from "react-native";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { memberTokenState } from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { RiderApi } from "../../../utils/api/RiderApi";
import { ridersInfoState } from "../../../state/rider/RiderState";
import { QueryApi } from "../../../utils/api/QueryApi";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import { certificationsState } from "../../../state/certification/CertificationState";
import { COLOR } from "../../../utils/constants";
import RaceCertificationImages from "./RaceCertificationImages";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceSpec from "./RaceSpec";
import LinkCopyButton from "./LinkCopyButton";
import { navigateWithHistory } from "../../../utils/util";
import { useNavigation } from "@react-navigation/core";
import { CalculationApi } from "../../../utils/api/CalculationApi";
import HalfWidthButton from "./HalfWidthButton";

const RaceDetail = ({ route }) => {
  const raceId = route.params.id;
  const navigation = useNavigation();
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const [ridersInfo, setRidersInfo] = useRecoilState(ridersInfoState);
  const setCertificationsInfo = useSetRecoilState(certificationsState);

  const calculateRace = () => {
    CalculationApi.post(token, raceId).catch((e) =>
      alert(e.response.data.code),
    );
  };

  const navigateToRaceCalculation = () => {
    navigateWithHistory(navigation, [
      {
        name: "Home",
      },
      {
        name: "RaceDetail",
        params: {
          id: raceInfo.id,
        },
      },
      {
        name: "RaceCalculation",
        params: {
          id: raceInfo.id,
        },
      },
    ]);
  };

  useEffect(() => {
    setIsLoading(true);
    const fetchRace = async () => {
      if (!raceId) {
        Alert.alert("", "잘못된 경로입니다.");
        setIsLoading(false);
        return;
      }
      try {
        const race = await QueryApi.getRaceDetail(token, raceId);
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
      } catch (e) {
        Alert.alert("", e.response.data.message);
      }
    };
    fetchRace();
    setIsLoading(false);
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
          days={raceInfo.days}
          raceDuration={raceInfo.race_duration}
          missionDuration={raceInfo.mission_duration}
          cash={raceInfo.entrance_fee}
          riderCount={ridersInfo.length}
        />
        <View style={styles.linkButton}>
          <LinkCopyButton raceId={raceId} />
        </View>
        <View style={styles.calculationButton}>
          <HalfWidthButton
            color={COLOR.BLUE3}
            children={"정산하기"}
            onClick={calculateRace}
          />
          <HalfWidthButton
            color={COLOR.BLUE5}
            children={"정산결과보기"}
            onClick={navigateToRaceCalculation}
          />
        </View>
      </ScrollView>
    </LoadingIndicator>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE,
  },
  linkButton: {
    marginBottom: 55,
  },
  calculationButton: {
    flexDirection: "row",
    position: "absolute",
    bottom: 0,
  },
});

export default RaceDetail;
