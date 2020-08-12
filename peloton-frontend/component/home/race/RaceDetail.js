import React, { useEffect } from "react";
import { Clipboard, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import RaceDetailInfo from "./RaceDetailInfo";
import RaceCertificationImage from "./RaceCertificationImage";
import RaceSpec from "./RaceSpec";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "../../../state/loading/LoadingState";
import { COLOR, DEEP_LINK_BASE_URL } from "../../../utils/constants";
import { useRecoilState } from "recoil/dist";
import { memberTokenState } from "../../../state/member/MemberState";
import { raceInfoState } from "../../../state/race/RaceState";
import { RaceApi } from "../../../utils/api/RaceApi";
import { Feather } from "@expo/vector-icons";

const calculateTotalCash = (cash) => {
  return cash * 10;
};

const RaceDetail = ({ route }) => {
  const token = useRecoilValue(memberTokenState);
  const setIsLoading = useSetRecoilState(loadingState);
  const raceId = route.params.location.split("/")[3];
  const [raceInfo, setRaceInfo] = useRecoilState(raceInfoState);

  useEffect(() => {
    if (!raceInfo) {
      const fetchRace = async () => {
        setIsLoading(true);
        const response = await RaceApi.get(raceId, token);
        setRaceInfo(response.data);
      };
      fetchRace();
    }
    setIsLoading(false);
  }, []);

  const copyLink = async () => {
    console.log(`${DEEP_LINK_BASE_URL}races/${raceId}`);
    Clipboard.setString(`${DEEP_LINK_BASE_URL}races/${raceId}`);
    alert("클립보드에 복사되었습니다.");
  };

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
      <View style={styles.linkCopyButtonContainer}>
        <TouchableOpacity style={styles.copyButton} onPress={copyLink}>
          <Text style={styles.copyText}>링크 복사하기</Text>
          <Feather name="clipboard" size={24} color={COLOR.WHITE} />
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLOR.WHITE,
  },
  linkCopyButtonContainer: {
    height: 100,
    justifyContent: "center",
    alignItems: "center",
  },
  copyButton: {
    width: 200,
    height: 50,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: COLOR.BLUE4,
  },
  copyText: {
    color: COLOR.WHITE,
    fontSize: 25,
  },
});

export default RaceDetail;
