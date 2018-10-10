package by.epam.auction.command;

import by.epam.auction.command.administrator.BlockUserCommand;
import by.epam.auction.command.administrator.ShowUserSetCommand;
import by.epam.auction.command.lot.BidCommand;
import by.epam.auction.command.lot.ProposeLotCommand;
import by.epam.auction.command.administrator.CreateAuctionCommand;
import by.epam.auction.command.page.ViewLot;
import by.epam.auction.command.page.ViewLotSet;
import by.epam.auction.command.user.LoginCommand;
import by.epam.auction.command.user.LogoutCommand;
import by.epam.auction.command.user.RegisterCommand;
import by.epam.auction.command.page.ViewAuctionSet;
import by.epam.auction.service.AdministratorService;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.LotService;
import by.epam.auction.service.UserService;

/**
 * List of suitable commands.
 */
public enum CommandType {
    LOG_IN(new LoginCommand(new UserService())),
    LOG_OUT(new LogoutCommand(new UserService())),
	
    REGISTER(new RegisterCommand(new UserService())),
    BID(new BidCommand(new LotService())),
    PROPOSE_LOT(new ProposeLotCommand(new LotService())),
    
    CREATE_AUCTION(new CreateAuctionCommand(new AuctionService())),
    BlOCK_USER(new BlockUserCommand(new AdministratorService())),
    //TODO unblock user
    VIEW_USER_SET(new ShowUserSetCommand(new AdministratorService())),
    
    VIEW_AUCTION_SET(new ViewAuctionSet(new AuctionService())),
    VIEW_LOT_SET(new ViewLotSet(new LotService())),
    VIEW_LOT(new ViewLot(new LotService())),
	
    EMPTY_COMMAND(new EmptyCommand());

    /**
     * Command entity.
     */
    private Command command;

    /**
     * Constructor to instantiate command.
     *
     * @param command
     *            Command to instantiate.
     */
    CommandType(final Command command) {
        this.command = command;
    }

    /**
     * Getter on command.
     *
     * @return This command.
     */
    public Command getCommand() {
        return command;
    }
}
