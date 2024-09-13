wait(2)

local SG = game:GetService("StarterGui")
local CollectionService = game:GetService("CollectionService")
local shopParts = CollectionService:GetTagged("ShopPartTag")
local player = game:GetService("Players").LocalPlayer
local gemShopParts = CollectionService:GetTagged("GemShopPartTag")

local shopMenu = player.PlayerGui.Menu
local gemMenu = player.PlayerGui.GemMenu

local function openShopOnTouchPart (arrayOfShops,menuToOpen)
	for i,v in arrayOfShops do
		v.Touched:Connect(function(hit)
			if hit.Parent:FindFirstChild("Humanoid") then
				if player ~= game:GetService("Players"):GetPlayerFromCharacter(hit.Parent) then
					return
				end
				if player:HasTag("InShop") then
					return 
				end
				menuToOpen.Enabled = true

			end
		end)
	end
end

openShopOnTouchPart(shopParts,shopMenu)
openShopOnTouchPart(gemShopParts,gemMenu)
