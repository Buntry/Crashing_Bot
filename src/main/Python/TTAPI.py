from TikTokApi import TikTokApi
import sys

# SEE https://github.com/davidteather/TikTok-Api/issues/311#issuecomment-721164493

# Starts TikTokApi
api = TikTokApi.get_instance()

# This is generating the tt_webid_v2 cookie
# need to pass it to methods you want to download
device_id = api.generate_device_id()

#for whatever reason this line doesnt seem to be working
#trending = api.trending(custom_device_id=device_id)

#Set the URl of the video I want to download
try:
    url = str(sys.argv[1])
    video = api.get_tiktok_by_url(url)
except:
    url = "https://www.tiktok.com/@tehdanboy/video/7013197933822643462?sender_device=pc&sender_web_id=6943458800746104325&is_from_webapp=v1&is_copy_url=0"
    video = api.get_tiktok_by_url(url)
attr = {}
attr["Title"] = video["itemInfo"]["itemStruct"]["desc"]
attr["Author"] = video["itemInfo"]["itemStruct"]['author']['uniqueId']
attr["pfp"] = video["itemInfo"]["itemStruct"]['author']['avatarThumb']

# Below is if the method used if you have the full tiktok object
video_bytes = api.get_video_by_tiktok(video, custom_device_id=device_id)

with open("Media/video.mp4", "wb") as out:
    out.write(video_bytes)

with open("Media/Attributes.json","w") as out:
    out.write(str(attr))