local ReplicatedStorage = game:GetService("ReplicatedStorage")
local openGuiEvent = ReplicatedStorage:WaitForChild("OpenGuiEvent")

local function openGui(guiName)
	openGuiEvent:Fire(guiName)
end

script.Parent.MouseButton1Up:Connect(function()
	local guiName = script.Parent:GetTags()[1]
	game:GetService("Players").LocalPlayer:SetAttribute("GUIOpen", guiName)
	openGui(guiName)
end)
