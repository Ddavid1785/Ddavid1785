local Players = game:GetService("Players")
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local openGuiEvent = ReplicatedStorage:WaitForChild("OpenGuiEvent")
local ToggleFrames = ReplicatedStorage:WaitForChild("ToggleFrames")

local player = Players.LocalPlayer
local guiOpenRN

local currentFrame  

local SubGuis = {
	InventoryGui = {
	["BatonsFrame"] = false,
	["PetsFrame"] = true,
	["StatsFrame"] = false
	};
	ShopGui = {
		["BatonShop"] = true,
		["CrateChoose"] = false,
		["CoinShop"] = false,
		["GamepassShop"] = false,
		["VIPShop"] = false,
		["BatonCrates"] = false,
		["PetCrates"] = false,
	}
}


local function handleGuiSwitch(guiClicked)
	if guiOpenRN == guiClicked then
		player.PlayerGui[guiOpenRN].Enabled = not player.PlayerGui[guiOpenRN].Enabled
		if not player.PlayerGui[guiOpenRN].Enabled then
			guiOpenRN = nil
			player:SetAttribute("GUIOpen", nil)
			end
	else
		if guiOpenRN then
			player.PlayerGui[guiOpenRN].Enabled = false
		end
		guiOpenRN = guiClicked
		player:SetAttribute("GUIOpen", guiClicked)
		player.PlayerGui[guiOpenRN].Enabled = true
	end

end

local function handleFrameSwitch(frameClicked, parentGui)
	local guiFrames = SubGuis[parentGui]
	if guiFrames then
		for frameName, isVisible in guiFrames do
			if frameName == frameClicked then
				local newVisibility = not isVisible  
				guiFrames[frameName] = newVisibility  
				player.PlayerGui[parentGui][frameName].Visible = newVisibility 
			else
				guiFrames[frameName] = false
				player.PlayerGui[parentGui][frameName].Visible = false
			end
		end
	end
end

guiOpenRN = player:GetAttribute("GUIOpen")
if guiOpenRN then
	handleGuiSwitch(guiOpenRN)
end

ToggleFrames.Event:Connect(function(frameName,frameParent)
	handleFrameSwitch(frameName,frameParent)
end)

openGuiEvent.Event:Connect(function(guiClicked)
	handleGuiSwitch(guiClicked)
end)
