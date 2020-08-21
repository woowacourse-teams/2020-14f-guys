import React, { useEffect } from "react";
import { Alert, ScrollView, StyleSheet, View } from "react-native";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/core";

import { COLOR } from "../../../utils/constants";
import { loadingState } from "../../../state/loading/LoadingState";
import { memberTokenState } from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { ridersInfoState } from "../../../state/rider/RiderState";
import { certificationsState } from "../../../state/certification/CertificationState";
import { RiderApi } from "../../../utils/api/RiderApi";
import { QueryApi } from "../../../utils/api/QueryApi";
import LoadingIndicator from "../../../utils/LoadingIndicator";
import LinkCopyButton from "./LinkCopyButton";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImages from "./RaceCertificationImages";
import RaceSpec from "./RaceSpec";
import { navigateWithHistory } from "../../../utils/util";
import FullWidthButton from "./FullWidthButton";

const RaceDetail = ({ route }) => {
  const raceId = route.params.id;
  const navigation = useNavigation();
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);
  const [ridersInfo, setRidersInfo] = useRecoilState(ridersInfoState);
  const setCertificationsInfo = useSetRecoilState(certificationsState);

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

  const navigateToRaceCalculation = () => {
    navigateWithHistory(navigation, [
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
        <View style={styles.linkButton}>
          <LinkCopyButton raceId={raceId} />
        </View>
        <View style={styles.calculationButton}>
          <FullWidthButton
            color={COLOR.BLUE3}
            children={"정산하기"}
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
    position: "absolute",
    bottom: 0,
    backgroundColor: COLOR.RED,
  },
});

export default RaceDetail;
