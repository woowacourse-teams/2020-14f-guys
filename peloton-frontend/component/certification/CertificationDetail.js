import React, { useEffect } from "react";
import { Image, StyleSheet, Text, View } from "react-native";
import { useRecoilState } from "recoil";
import { useRecoilValue, useSetRecoilState } from "recoil/dist";
import {
  memberInfoState,
  memberTokenState,
} from "../../state/member/MemberState";
import { loadingState } from "../../state/loading/LoadingState";
import LoadingIndicator from "../../utils/LoadingIndicator";
import { COLOR, deviceHeight, deviceWidth } from "../../utils/constants";
import { RiderApi } from "../../utils/api/RiderApi";
import { MissionApi } from "../../utils/api/MissionApi";
import { missionInfoState } from "../../state/mission/MissionState";
import { MemberApi } from "../../utils/api/MemberApi";
import { customDateTimeParser, customTimeParser } from "../../utils/util";

const CertificationDetail = ({ route }) => {
  const setIsLoading = useSetRecoilState(loadingState);
  const newCertification = route.params.item;
  const [mission, setMission] = useRecoilState(missionInfoState);
  const [member, setMember] = useRecoilState(memberInfoState);
  const token = useRecoilValue(memberTokenState);

  useEffect(() => {
    setIsLoading(true);
    const { mission_id: missionId, rider_id: riderId } = newCertification;
    const fetchData = async () => {
      const newMission = await MissionApi.get(token, missionId);
      const newRider = await RiderApi.get(token, riderId);
      const newMember = await MemberApi.getById(token, newRider.member_id);
      setMission(newMission);
      setMember(newMember);
      setIsLoading(false);
    };
    fetchData().catch((error) => console.log(error));
  }, []);

  return newCertification && mission ? (
    <LoadingIndicator>
      <View style={styles.container}>
        <View style={styles.certificationImageContainer}>
          <Image
            style={styles.certificationImage}
            source={
              newCertification
                ? { uri: newCertification.image }
                : require("../../assets/empty-image.jpeg")
            }
            defaultSource={require("../../assets/empty-image.jpeg")}
          />
        </View>
        <View style={styles.memberInfoContainer}>
          <Image
            style={styles.memberProfileImage}
            source={
              member
                ? { uri: member.profile }
                : require("../../assets/default-profile.jpg")
            }
            defaultSource={require("../../assets/default-profile.jpg")}
          />
          <View style={styles.memberProfileTextContainer}>
            <Text style={styles.memberProfileText}>{member.name}</Text>
            <Text style={styles.certificationDateText}>
              {customDateTimeParser(newCertification.created_at)}
            </Text>
          </View>
          <View style={styles.certificationStatus}>
            <Text style={styles.statusText}>{newCertification.status}</Text>
          </View>
        </View>
        <View style={styles.border} />
        <View style={styles.certificationInfo}>
          <View style={styles.missionTitle}>
            <Text style={styles.missionInstruction}>
              {mission.mission_instruction}
            </Text>
          </View>
          <View style={styles.missionDetail}>
            <Text style={styles.description}>
              {customTimeParser(mission.mission_duration.start_time)} ~{" "}
              {customTimeParser(mission.mission_duration.end_time)}
            </Text>
          </View>
        </View>
      </View>
    </LoadingIndicator>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  certificationImageContainer: {
    width: deviceWidth,
    height: deviceHeight * 0.5,
  },
  certificationStatus: {
    width: 60,
    height: 30,
    borderRadius: 15,
    backgroundColor: COLOR.GREEN4,
    justifyContent: "center",
    alignItems: "center",
    right: 10,
    position: "absolute",
  },
  certificationImage: {
    width: deviceWidth,
    height: deviceHeight * 0.5,
    resizeMode: "cover",
  },
  certificationDateText: {
    fontWeight: "200",
    marginVertical: 3,
  },
  certificationInfo: {
    flex: 1,
    marginHorizontal: 10,
  },
  description: {
    fontSize: 14,
    fontWeight: "200",
  },
  statusText: {
    fontSize: 12,
    color: COLOR.WHITE,
  },
  reportButtonContainer: {
    position: "absolute",
    bottom: 0,
  },
  memberInfoContainer: {
    justifyContent: "flex-start",
    alignItems: "center",
    flexDirection: "row",
    marginLeft: 10,
    marginVertical: 15,
  },
  memberProfileImage: {
    width: 50,
    height: 50,
    borderRadius: 25,
    marginHorizontal: 5,
  },
  memberProfileText: {
    fontSize: 16,
    fontWeight: "bold",
    marginVertical: 3,
  },
  border: {
    alignSelf: "center",
    width: deviceWidth * 0.95,
    borderBottomWidth: 1,
    borderColor: COLOR.GRAY3,
    marginBottom: 15,
  },
  missionInstruction: {
    fontSize: 20,
    fontWeight: "bold",
  },
  memberProfileTextContainer: {
    justifyContent: "space-between",
  },
  missionTitle: {
    marginVertical: 10,
  },
  missionDetail: {
    flexDirection: "row",
    marginTop: 10,
  },
});

export default CertificationDetail;
