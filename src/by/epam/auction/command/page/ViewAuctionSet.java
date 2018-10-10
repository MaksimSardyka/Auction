package by.epam.auction.command.page;

import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.command.Command;
import by.epam.auction.command.CommandType;
import by.epam.auction.constant.ParsingValues;
import by.epam.auction.content.SessionRequestContent;
import by.epam.auction.domain.Auction;
import by.epam.auction.service.AuctionService;
import by.epam.auction.service.exception.ServiceException;

public class ViewAuctionSet implements Command{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Service to work with auction.
     */
    AuctionService service;

    /**
     * Constructor.
     *
     * @param service
     *            Service to use to work with auction.
     */
    public ViewAuctionSet(AuctionService service) {
        this.service = service;
    }

    /**
     * Operation to perform on show lot.
     */
    @Override
    public ViewPage execute(SessionRequestContent requestContent){
        LOG.log(Level.DEBUG, "Perform " + CommandType.VIEW_AUCTION_SET.name());
        ViewPage page = ViewPage.NULL_PAGE;
        
        try {
            Optional<Set<Auction>> optionalAuctionSet = service.getAll();
            if (optionalAuctionSet.isPresent()) {
                LOG.log(Level.DEBUG, "Found " + optionalAuctionSet.get().size() + " auctions");
                requestContent.insertRequestAttribute(ParsingValues.AUCTION_SET, optionalAuctionSet.get());
                page = ViewPage.VIEW_AUCTION_SET;
            } else {
                LOG.log(Level.ERROR, "No auctions found");
            }
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Find auction service error");
            page = CommandType.EMPTY_COMMAND.getCommand().execute(requestContent);
        }
        
        return page;
    }
}